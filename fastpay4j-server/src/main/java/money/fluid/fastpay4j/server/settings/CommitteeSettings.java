package money.fluid.fastpay4j.server.settings;

import org.immutables.value.Value;

import java.util.List;

/**
 * Runtime configuration properties for the committees used by this server.
 */
public interface CommitteeSettings {

  @Value.Immutable
  abstract class DefaultCommitteeSettings implements CommitteeSettings {

    /**
     * The authorities for this committee.
     */
    public abstract List<AuthoritySettings> authorities();
  }
}
